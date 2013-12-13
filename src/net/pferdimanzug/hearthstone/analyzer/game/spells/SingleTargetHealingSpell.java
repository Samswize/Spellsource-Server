package net.pferdimanzug.hearthstone.analyzer.game.spells;

import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;

public class SingleTargetHealingSpell extends HealingSpell {

	public SingleTargetHealingSpell(int healing) {
		super(healing);
	}

	@Override
	public void cast(GameContext context, Player player, Entity target) {
		context.getLogic().heal(target, getHealing());
	}

}
