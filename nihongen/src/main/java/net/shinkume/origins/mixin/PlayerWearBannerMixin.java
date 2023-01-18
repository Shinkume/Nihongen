package net.shinkume.origins.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class PlayerWearBannerMixin {

    @Shadow public abstract void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected);

    @Inject(method = "use", at = @At(value = "HEAD"))
    public void wearBanner(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir)
{
    if(user.getMainHandStack().getItem() instanceof BannerItem)
    {
        if(user.getEquippedStack(EquipmentSlot.HEAD).isEmpty() && user.getMainHandStack().getCount() ==1)
        {
                ItemStack itemStack;

                itemStack = user.getMainHandStack();

            user.equipStack(EquipmentSlot.HEAD, itemStack);

            user.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
        }
    }
}

}
