package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Random;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;

public class SetNBT extends LootFunction
{
    private final NBTTagCompound tag;

    public SetNBT(LootCondition[] conditionsIn, NBTTagCompound tagIn)
    {
        super(conditionsIn);
        this.tag = tagIn;
    }

    public ItemStack apply(ItemStack stack, Random rand, LootContext context)
    {
        NBTTagCompound nbttagcompound = stack.getTagCompound();

        if (nbttagcompound == null)
        {
            nbttagcompound = this.tag.copy();
        }
        else
        {
            nbttagcompound.merge(this.tag);
        }

        stack.setTagCompound(nbttagcompound);
        return stack;
    }

    public static class Serializer extends LootFunction.Serializer<SetNBT>
    {
        public Serializer()
        {
            super(new ResourceLocation("set_nbt"), SetNBT.class);
        }

        public void serialize(JsonObject object, SetNBT functionClazz, JsonSerializationContext serializationContext)
        {
            object.addProperty("tag", functionClazz.tag.toString());
        }

        public SetNBT deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootCondition[] conditionsIn)
        {
            try
            {
                NBTTagCompound nbttagcompound = JsonToNBT.getTagFromJson(JsonUtils.getString(object, "tag"));
                return new SetNBT(conditionsIn, nbttagcompound);
            }
            catch (NBTException nbtexception)
            {
                throw new JsonSyntaxException(nbtexception);
            }
        }
    }
}
