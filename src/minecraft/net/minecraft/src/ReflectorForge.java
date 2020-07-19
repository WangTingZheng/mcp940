package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ReflectorForge
{
    public static void FMLClientHandler_trackBrokenTexture(ResourceLocation p_FMLClientHandler_trackBrokenTexture_0_, String p_FMLClientHandler_trackBrokenTexture_1_)
    {
        if (!Reflector.FMLClientHandler_trackBrokenTexture.exists())
        {
            Object object = Reflector.call(Reflector.FMLClientHandler_instance);
            Reflector.call(object, Reflector.FMLClientHandler_trackBrokenTexture, p_FMLClientHandler_trackBrokenTexture_0_, p_FMLClientHandler_trackBrokenTexture_1_);
        }
    }

    public static void FMLClientHandler_trackMissingTexture(ResourceLocation p_FMLClientHandler_trackMissingTexture_0_)
    {
        if (!Reflector.FMLClientHandler_trackMissingTexture.exists())
        {
            Object object = Reflector.call(Reflector.FMLClientHandler_instance);
            Reflector.call(object, Reflector.FMLClientHandler_trackMissingTexture, p_FMLClientHandler_trackMissingTexture_0_);
        }
    }

    public static void putLaunchBlackboard(String p_putLaunchBlackboard_0_, Object p_putLaunchBlackboard_1_)
    {
        Map map = (Map)Reflector.getFieldValue(Reflector.Launch_blackboard);

        if (map != null)
        {
            map.put(p_putLaunchBlackboard_0_, p_putLaunchBlackboard_1_);
        }
    }

    public static boolean renderFirstPersonHand(RenderGlobal p_renderFirstPersonHand_0_, float p_renderFirstPersonHand_1_, int p_renderFirstPersonHand_2_)
    {
        return !Reflector.ForgeHooksClient_renderFirstPersonHand.exists() ? false : Reflector.callBoolean(Reflector.ForgeHooksClient_renderFirstPersonHand, p_renderFirstPersonHand_0_, p_renderFirstPersonHand_1_, p_renderFirstPersonHand_2_);
    }

    public static InputStream getOptiFineResourceStream(String p_getOptiFineResourceStream_0_)
    {
        if (!Reflector.OptiFineClassTransformer_instance.exists())
        {
            return null;
        }
        else
        {
            Object object = Reflector.getFieldValue(Reflector.OptiFineClassTransformer_instance);

            if (object == null)
            {
                return null;
            }
            else
            {
                if (p_getOptiFineResourceStream_0_.startsWith("/"))
                {
                    p_getOptiFineResourceStream_0_ = p_getOptiFineResourceStream_0_.substring(1);
                }

                byte[] abyte = (byte[])Reflector.call(object, Reflector.OptiFineClassTransformer_getOptiFineResource, p_getOptiFineResourceStream_0_);

                if (abyte == null)
                {
                    return null;
                }
                else
                {
                    InputStream inputstream = new ByteArrayInputStream(abyte);
                    return inputstream;
                }
            }
        }
    }

    public static boolean blockHasTileEntity(IBlockState p_blockHasTileEntity_0_)
    {
        Block block = p_blockHasTileEntity_0_.getBlock();
        return !Reflector.ForgeBlock_hasTileEntity.exists() ? block.hasTileEntity() : Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, p_blockHasTileEntity_0_);
    }

    public static boolean isItemDamaged(ItemStack p_isItemDamaged_0_)
    {
        return !Reflector.ForgeItem_showDurabilityBar.exists() ? p_isItemDamaged_0_.isItemDamaged() : Reflector.callBoolean(p_isItemDamaged_0_.getItem(), Reflector.ForgeItem_showDurabilityBar, p_isItemDamaged_0_);
    }

    public static boolean armorHasOverlay(ItemArmor p_armorHasOverlay_0_, ItemStack p_armorHasOverlay_1_)
    {
        if (Reflector.ForgeItemArmor_hasOverlay.exists())
        {
            return Reflector.callBoolean(p_armorHasOverlay_0_, Reflector.ForgeItemArmor_hasOverlay, p_armorHasOverlay_1_);
        }
        else
        {
            int i = p_armorHasOverlay_0_.getColor(p_armorHasOverlay_1_);
            return i != 16777215;
        }
    }
}
