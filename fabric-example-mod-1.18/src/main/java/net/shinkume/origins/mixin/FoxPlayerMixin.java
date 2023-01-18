package net.shinkume.origins.mixin;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.Hand;
import net.shinkume.origins.powers.FoxMorphPower;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.OptionalInt;

@Mixin(PlayerEntity.class)
public abstract class FoxPlayerMixin {


    @Shadow public abstract OptionalInt openHandledScreen(@Nullable NamedScreenHandlerFactory factory);

    @Shadow public abstract float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions);

    @Inject(method = "tickMovement", at = @At(value = "HEAD"))
    public void onMove(CallbackInfo ci)
    {
       PlayerEntity player = (PlayerEntity) (Object) this;

       if(player.getScoreboardTags().contains("fox"))
       {

           FoxEntity entity = FoxMorphPower.fox;

if(entity == null) return;

           if (entity instanceof NamedScreenHandlerFactory) {
               this.openHandledScreen((NamedScreenHandlerFactory)entity);
           }



            entity.headYaw = player.headYaw;
           entity.setPos(player.getX(), player.getY(), player.getZ());
           entity.setHeadYaw(player.getHeadYaw());
           entity.setJumping(player.getVelocity().getY() > 0);
           entity.setSprinting(player.isSprinting());
           entity.setStuckArrowCount(player.getStuckArrowCount());
           entity.setInvulnerable(true);
           entity.setNoGravity(true);
           entity.setSneaking(player.isSneaking());
           entity.setSwimming(player.isSwimming());
           entity.setCurrentHand(player.getActiveHand());
           entity.horizontalCollision = false;
           entity.verticalCollision = false;
           //entity.setPose(player.getPose());
           entity.setStackInHand(Hand.MAIN_HAND, player.getMainHandStack());
           entity.setPose(EntityPose.STANDING);

       }
    }




}
