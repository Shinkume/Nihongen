package net.shinkume.origins.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.shinkume.origins.powers.FoxMorphPower;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @Shadow @Nullable private Entity cameraEntity;

    @Shadow public abstract Entity getCameraEntity();

    @Inject(method = "onDisconnect", at = @At("HEAD"))
public void removeFox(CallbackInfo ci)
{

if(this.getCameraEntity() == null) return;

    if(FoxMorphPower.fox == null) return;
       if(FoxMorphPower.fox.getScoreboardTags().contains(this.getCameraEntity().getName().asString()))
       {
            FoxMorphPower.fox.remove(Entity.RemovalReason.DISCARDED);

       }
}
}
