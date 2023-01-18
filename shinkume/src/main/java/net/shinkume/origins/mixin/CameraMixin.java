package net.shinkume.origins.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.shinkume.origins.powers.FoxMorphPower;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow private boolean ready;

    @Shadow private BlockView area;

    @Shadow private Entity focusedEntity;

    @Shadow private boolean thirdPerson;

    @Shadow protected abstract void setRotation(float yaw, float pitch);

    @Shadow protected abstract void setPos(Vec3d pos);

    @Shadow protected abstract void moveBy(double x, double y, double z);

    @Shadow protected abstract double clipToSpace(double desiredCameraDistance);

    @Shadow private float yaw;

    @Shadow private float pitch;

    @Shadow private float cameraY;

    @Shadow private float lastCameraY;

    @Overwrite
   public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta) {
       this.ready = true;
       this.area = area;
       if(FoxMorphPower.active)
       {
       if(FoxMorphPower.fox != null)    this.focusedEntity = FoxMorphPower.fox;
       }else
       {
           this.focusedEntity = focusedEntity;
       }

       this.thirdPerson = thirdPerson;
       this.setRotation(focusedEntity.getYaw(tickDelta), focusedEntity.getPitch(tickDelta));
       this.setPos(new Vec3d(MathHelper.lerp((double)tickDelta, focusedEntity.prevX, focusedEntity.getX()), MathHelper.lerp((double)tickDelta, focusedEntity.prevY, focusedEntity.getY()) + (double)MathHelper.lerp(tickDelta, this.lastCameraY, this.cameraY), MathHelper.lerp((double)tickDelta, focusedEntity.prevZ, focusedEntity.getZ())));
       if (thirdPerson) {
           if (inverseView) {
               this.setRotation(this.yaw + 180.0F, -this.pitch);
           }

           this.moveBy(-this.clipToSpace(4.0D), 0.0D, 0.0D);
       } else if (focusedEntity instanceof LivingEntity && ((LivingEntity)focusedEntity).isSleeping()) {
           Direction direction = ((LivingEntity)focusedEntity).getSleepingDirection();
           this.setRotation(direction != null ? direction.asRotation() - 180.0F : 0.0F, 0.0F);
           this.moveBy(0.0D, 0.3D, 0.0D);
       }

   }


}


