package net.shinkume.origins.powers;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

public class ShadowCloneJutsuPower extends Power implements Active {
    public ShadowCloneJutsuPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    private Key key;



    @Override
    public void onUse() {

        entity.addScoreboardTag("canUse");

    }

    @Override
    public Key getKey() {
        return this.key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }
}
