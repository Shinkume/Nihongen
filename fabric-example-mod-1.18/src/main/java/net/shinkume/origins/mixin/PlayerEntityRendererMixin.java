package net.shinkume.origins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.shinkume.origins.access.PlayerIllusionOffsetAccessor;
import net.shinkume.origins.powers.ShadowCloneJutsuPower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>>{

    @Shadow protected abstract void setModelPose(AbstractClientPlayerEntity player);

    private final Vec3d[][] clientsideillusionoffset = new Vec3d[2][4];
    boolean rendered = false;
    private final int illusionticks = 3;


    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void createIllusionOffset(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci)
    {
        for (int b = 0; b < 4; ++b) {
            this.clientsideillusionoffset[0][b] = Vec3d.ZERO;
            this.clientsideillusionoffset[1][b] = Vec3d.ZERO;
        }
    }

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }


    @Overwrite
    public void render(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {

            if (abstractClientPlayerEntity.getScoreboardTags().contains("canUse")) {

                Vec3d[] vec3ds = ((PlayerIllusionOffsetAccessor)abstractClientPlayerEntity).getIllusionOffsets(g);
                float h = (float) abstractClientPlayerEntity.age + g;

                for (int j = 0; j < vec3ds.length; ++j) {
                    matrixStack.push();
                    matrixStack.translate(vec3ds[j].x + (double) MathHelper.cos((float) j + h * 0.5F) * 0.025D, vec3ds[j].y + (double) MathHelper.cos((float) j + h * 0.75F) * 0.0125D, vec3ds[j].z + (double) MathHelper.cos((float) j + h * 0.7F) * 0.025D);
                    this.setModelPose(abstractClientPlayerEntity);

                    super.render(abstractClientPlayerEntity, f, g, matrixStack, vertexConsumerProvider, i);
                    matrixStack.pop();
                }
            } else {
                this.setModelPose(abstractClientPlayerEntity);
                super.render(abstractClientPlayerEntity, f, g, matrixStack, vertexConsumerProvider, i);
            }





    }



}
