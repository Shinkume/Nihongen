package net.shinkume.origins.mixin;

import com.mojang.authlib.GameProfile;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.shinkume.origins.powers.ShadowCloneJutsuPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements net.shinkume.origins.access.PlayerIllusionOffsetAccessor{

    private static final List<ItemStack> EMPTY_STACK_LIST = Collections.emptyList();
    private final Vec3d[][] clientSideIllusionOffsets = new Vec3d[2][4];

    public int clientSideIllusionTicks;
    private static final int NUM_ILLUSIONS = 4;
    private static final int ILLUSION_TRANSITION_TICKS = 3;
    private static final int ILLUSION_SPREAD = 3;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void init(World world, BlockPos pos, float yaw, GameProfile profile, CallbackInfo ci)
    {
                for (int i = 0; i < 4; ++i) {
            this.clientSideIllusionOffsets[0][i] = Vec3d.ZERO;
            this.clientSideIllusionOffsets[1][i] = Vec3d.ZERO;
        }
    }

    @Override
    public Vec3d[] getIllusionOffsets(float f) {
        if (this.clientSideIllusionTicks <= 0) {
            return this.clientSideIllusionOffsets[1];
        } else {
            double d = (double)(((float)this.clientSideIllusionTicks - f) / 3.0F);
            d = Math.pow(d, 0.25D);
            Vec3d[] vec3s = new Vec3d[4];


//                int j;
//            for (j = 0; j < 4; ++j) {
//                this.clientSideIllusionOffsets[0][j] = this.clientSideIllusionOffsets[1][j];
//                this.clientSideIllusionOffsets[1][j] = new Vec3d((double) (-6.0F + (float) this.random.nextInt(13)) * 0.5D, Math.max(0, this.random.nextInt(6) - 4), (double) (-6.0F + (float) this.random.nextInt(13)) * 0.5D);
//            }

            for(int i = 0; i < 4; ++i) {
                vec3s[i] = this.clientSideIllusionOffsets[1][i].multiply(1.0D - d).add(this.clientSideIllusionOffsets[0][i].multiply(d));
            }



            return vec3s;
        }
    }




    @Inject(method = "tickMovement", at = @At(value = "TAIL"))
    public void tickMovement(CallbackInfo ci) {



        if (this.world.isClient) {
            --this.clientSideIllusionTicks;
            if (this.clientSideIllusionTicks < 0) {

                this.clientSideIllusionTicks = 0;
            }

            if (this.hurtTime != 1 && this.age % 1200 != 0) {
                if (this.hurtTime == this.maxHurtTime - 1) {
                    this.clientSideIllusionTicks = 3;

                    for (int f = 0; f < 4; ++f) {
                        this.clientSideIllusionOffsets[0][f] = this.clientSideIllusionOffsets[1][f];
                        this.clientSideIllusionOffsets[1][f] = new Vec3d(0.0D, 0.0D, 0.0D);
                    }
                }
            } else {
                this.clientSideIllusionTicks = 3;
                float f = -6.0F;
                //int i = true;

                int j;
                for (j = 0; j < 4; ++j) {
                    this.clientSideIllusionOffsets[0][j] = this.clientSideIllusionOffsets[1][j];
                    this.clientSideIllusionOffsets[1][j] = new Vec3d((double) (-6.0F + (float) this.random.nextInt(13)) * 0.5D, Math.max(0, this.random.nextInt(6) - 4), (double) (-6.0F + (float) this.random.nextInt(13)) * 0.5D);
                }

                if(!this.getScoreboardTags().contains("canUse")) return;

                for (j = 0; j < 16; ++j) {
                    this.world.addParticle(ParticleTypes.CLOUD, this.getParticleX(0.5D), this.getRandomBodyY(), this.offsetZ(0.5D), 0.0D, 0.0D, 0.0D);
                }

                this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ILLUSIONER_MIRROR_MOVE, this.getSoundCategory(), 1.0F, 1.0F, false);



                if(this.getScoreboardTags().contains("canUse"))
                {
                    this.removeScoreboardTag("canUse");
                }
            }
        }
    }


}

