package net.shinkume.origins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.shinkume.origins.powers.ProjectilePower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowballEntity.class)
public class ProjectileDamageMixin {

@Inject(method = "onEntityHit", at = @At(value = "HEAD"))
    public void damage(EntityHitResult entityHitResult, CallbackInfo ci)
    {
        ProjectileEntity entity = (ProjectileEntity) (Object) this;

        if(PowerHolderComponent.hasPower(entity.getOwner(),ProjectilePower.class))
        {
            entity.damage(DamageSource.thrownProjectile(entity, entity.getOwner()), 5f);

        }

    }


}
