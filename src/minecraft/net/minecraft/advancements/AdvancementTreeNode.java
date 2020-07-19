package net.minecraft.advancements;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class AdvancementTreeNode
{
    private final Advancement advancement;
    private final AdvancementTreeNode parent;
    private final AdvancementTreeNode sibling;
    private final int index;
    private final List<AdvancementTreeNode> children = Lists.<AdvancementTreeNode>newArrayList();
    private AdvancementTreeNode ancestor;
    private AdvancementTreeNode thread;
    private int x;
    private float y;
    private float mod;
    private float change;
    private float shift;

    public AdvancementTreeNode(Advancement advancementIn, @Nullable AdvancementTreeNode parentIn, @Nullable AdvancementTreeNode siblingIn, int indexIn, int xIn)
    {
        if (advancementIn.getDisplay() == null)
        {
            throw new IllegalArgumentException("Can't position an invisible advancement!");
        }
        else
        {
            this.advancement = advancementIn;
            this.parent = parentIn;
            this.sibling = siblingIn;
            this.index = indexIn;
            this.ancestor = this;
            this.x = xIn;
            this.y = -1.0F;
            AdvancementTreeNode advancementtreenode = null;

            for (Advancement advancement : advancementIn.getChildren())
            {
                advancementtreenode = this.buildSubTree(advancement, advancementtreenode);
            }
        }
    }

    @Nullable
    private AdvancementTreeNode buildSubTree(Advancement advancementIn, @Nullable AdvancementTreeNode previous)
    {
        if (advancementIn.getDisplay() != null)
        {
            previous = new AdvancementTreeNode(advancementIn, this, previous, this.children.size() + 1, this.x + 1);
            this.children.add(previous);
        }
        else
        {
            for (Advancement advancement : advancementIn.getChildren())
            {
                previous = this.buildSubTree(advancement, previous);
            }
        }

        return previous;
    }

    private void firstWalk()
    {
        if (this.children.isEmpty())
        {
            if (this.sibling != null)
            {
                this.y = this.sibling.y + 1.0F;
            }
            else
            {
                this.y = 0.0F;
            }
        }
        else
        {
            AdvancementTreeNode advancementtreenode = null;

            for (AdvancementTreeNode advancementtreenode1 : this.children)
            {
                advancementtreenode1.firstWalk();
                advancementtreenode = advancementtreenode1.apportion(advancementtreenode == null ? advancementtreenode1 : advancementtreenode);
            }

            this.executeShifts();
            float f = ((this.children.get(0)).y + (this.children.get(this.children.size() - 1)).y) / 2.0F;

            if (this.sibling != null)
            {
                this.y = this.sibling.y + 1.0F;
                this.mod = this.y - f;
            }
            else
            {
                this.y = f;
            }
        }
    }

    private float secondWalk(float p_192319_1_, int p_192319_2_, float p_192319_3_)
    {
        this.y += p_192319_1_;
        this.x = p_192319_2_;

        if (this.y < p_192319_3_)
        {
            p_192319_3_ = this.y;
        }

        for (AdvancementTreeNode advancementtreenode : this.children)
        {
            p_192319_3_ = advancementtreenode.secondWalk(p_192319_1_ + this.mod, p_192319_2_ + 1, p_192319_3_);
        }

        return p_192319_3_;
    }

    private void thirdWalk(float yIn)
    {
        this.y += yIn;

        for (AdvancementTreeNode advancementtreenode : this.children)
        {
            advancementtreenode.thirdWalk(yIn);
        }
    }

    private void executeShifts()
    {
        float f = 0.0F;
        float f1 = 0.0F;

        for (int i = this.children.size() - 1; i >= 0; --i)
        {
            AdvancementTreeNode advancementtreenode = this.children.get(i);
            advancementtreenode.y += f;
            advancementtreenode.mod += f;
            f1 += advancementtreenode.change;
            f += advancementtreenode.shift + f1;
        }
    }

    @Nullable
    private AdvancementTreeNode getFirstChild()
    {
        if (this.thread != null)
        {
            return this.thread;
        }
        else
        {
            return !this.children.isEmpty() ? (AdvancementTreeNode)this.children.get(0) : null;
        }
    }

    @Nullable
    private AdvancementTreeNode getLastChild()
    {
        if (this.thread != null)
        {
            return this.thread;
        }
        else
        {
            return !this.children.isEmpty() ? (AdvancementTreeNode)this.children.get(this.children.size() - 1) : null;
        }
    }

    private AdvancementTreeNode apportion(AdvancementTreeNode nodeIn)
    {
        if (this.sibling == null)
        {
            return nodeIn;
        }
        else
        {
            AdvancementTreeNode advancementtreenode = this;
            AdvancementTreeNode advancementtreenode1 = this;
            AdvancementTreeNode advancementtreenode2 = this.sibling;
            AdvancementTreeNode advancementtreenode3 = this.parent.children.get(0);
            float f = this.mod;
            float f1 = this.mod;
            float f2 = advancementtreenode2.mod;
            float f3;

            for (f3 = advancementtreenode3.mod; advancementtreenode2.getLastChild() != null && advancementtreenode.getFirstChild() != null; f1 += advancementtreenode1.mod)
            {
                advancementtreenode2 = advancementtreenode2.getLastChild();
                advancementtreenode = advancementtreenode.getFirstChild();
                advancementtreenode3 = advancementtreenode3.getFirstChild();
                advancementtreenode1 = advancementtreenode1.getLastChild();
                advancementtreenode1.ancestor = this;
                float f4 = advancementtreenode2.y + f2 - (advancementtreenode.y + f) + 1.0F;

                if (f4 > 0.0F)
                {
                    advancementtreenode2.getAncestor(this, nodeIn).moveSubtree(this, f4);
                    f += f4;
                    f1 += f4;
                }

                f2 += advancementtreenode2.mod;
                f += advancementtreenode.mod;
                f3 += advancementtreenode3.mod;
            }

            if (advancementtreenode2.getLastChild() != null && advancementtreenode1.getLastChild() == null)
            {
                advancementtreenode1.thread = advancementtreenode2.getLastChild();
                advancementtreenode1.mod += f2 - f1;
            }
            else
            {
                if (advancementtreenode.getFirstChild() != null && advancementtreenode3.getFirstChild() == null)
                {
                    advancementtreenode3.thread = advancementtreenode.getFirstChild();
                    advancementtreenode3.mod += f - f3;
                }

                nodeIn = this;
            }

            return nodeIn;
        }
    }

    private void moveSubtree(AdvancementTreeNode nodeIn, float p_192316_2_)
    {
        float f = (float)(nodeIn.index - this.index);

        if (f != 0.0F)
        {
            nodeIn.change -= p_192316_2_ / f;
            this.change += p_192316_2_ / f;
        }

        nodeIn.shift += p_192316_2_;
        nodeIn.y += p_192316_2_;
        nodeIn.mod += p_192316_2_;
    }

    private AdvancementTreeNode getAncestor(AdvancementTreeNode p_192326_1_, AdvancementTreeNode p_192326_2_)
    {
        return this.ancestor != null && p_192326_1_.parent.children.contains(this.ancestor) ? this.ancestor : p_192326_2_;
    }

    private void updatePosition()
    {
        if (this.advancement.getDisplay() != null)
        {
            this.advancement.getDisplay().setPosition((float)this.x, this.y);
        }

        if (!this.children.isEmpty())
        {
            for (AdvancementTreeNode advancementtreenode : this.children)
            {
                advancementtreenode.updatePosition();
            }
        }
    }

    public static void layout(Advancement root)
    {
        if (root.getDisplay() == null)
        {
            throw new IllegalArgumentException("Can't position children of an invisible root!");
        }
        else
        {
            AdvancementTreeNode advancementtreenode = new AdvancementTreeNode(root, (AdvancementTreeNode)null, (AdvancementTreeNode)null, 1, 0);
            advancementtreenode.firstWalk();
            float f = advancementtreenode.secondWalk(0.0F, 0, advancementtreenode.y);

            if (f < 0.0F)
            {
                advancementtreenode.thirdWalk(-f);
            }

            advancementtreenode.updatePosition();
        }
    }
}
