package net.shinkume.origins.powers;

import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;

public class FoxMorphPower extends Power implements Active{

    private Active.Key key;

   public static boolean active = false;

public static FoxEntity fox;

    public FoxMorphPower(PowerType<?> type, LivingEntity entity) {

        super(type, entity);


    }

    @Override
    public void onUse() {
        active = entity.isInvisible();

if(active == false)
{
    FoxEntity foxEntity = new FoxEntity(EntityType.FOX, entity.world);

    entity.world.spawnEntity(foxEntity);
    entity.addScoreboardTag("fox");
    entity.horizontalCollision = false;
    entity.verticalCollision = false;
    fox = foxEntity;
    fox.addScoreboardTag(entity.getName().asString());
    active = true;
    return;
}else if (active)
{
    entity.removeScoreboardTag("fox");
   if(fox != null) fox.remove(Entity.RemovalReason.DISCARDED);
    active = false;
}
        return;
    }

    @Override
    public Active.Key getKey() {
        return this.key;
    }

    @Override
    public void setKey(Active.Key key) {
        this.key = key;
    }


}
