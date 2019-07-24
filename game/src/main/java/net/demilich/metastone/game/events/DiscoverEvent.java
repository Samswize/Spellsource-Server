package net.demilich.metastone.game.events;

import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;

public class DiscoverEvent extends GameEvent {

	private final Entity target;
	private final Entity source;
	private final CardType targetType;

	public DiscoverEvent(GameContext context, Entity source, Entity target, CardType targetType) {
		super(context, target.getOwner(), -1);
		this.target = target;
		this.source = source;
		this.targetType = targetType;
	}

	@Override
	public Entity getEventSource() {
		return source;
	}

	@Override
	public Entity getEventTarget() {
		return target;
	}


	public CardType getTargetType() {
		return targetType;
	}

	@Override
	public GameEventType getEventType() {
		return GameEventType.DISCOVER;
	}

	@Override
	public boolean isClientInterested() {
		return true;
	}
}