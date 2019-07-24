package net.demilich.metastone.game.spells.trigger;

import net.demilich.metastone.game.cards.CardType;
import net.demilich.metastone.game.entities.Entity;
import net.demilich.metastone.game.events.DiscoverEvent;
import net.demilich.metastone.game.events.GameEvent;
import net.demilich.metastone.game.events.GameEventType;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerArg;
import net.demilich.metastone.game.spells.desc.trigger.EventTriggerDesc;

/**
 * For examples:
 *
 *   "description": "Whenever you discover a minion, add a Lunstone to your hand.",
 *   "trigger": {
 *     "eventTrigger": {
 *       "class": "DiscoverTrigger",
 *       "cardType": "MINION"
 *     },
 *     "spell": {
 *       "class": "ReceiveCardSpell",
 *       "card": "spell_lunstone"
 *     }
 *   },
 *
 *   "description": "Whenever you discover a spell, add a Lunstone to your hand.",
 *   "trigger": {
 *     "eventTrigger": {
 *       "class": "DiscoverTrigger",
 *       "cardType": "SPELL"
 *     },
 *     "spell": {
 *       "class": "ReceiveCardSpell",
 *       "card": "spell_lunstone"
 *     }
 *   },
 */

public class DiscoverTrigger extends EventTrigger {

	public DiscoverTrigger(EventTriggerDesc desc) {
		super(desc);
	}

	@Override
	protected boolean innerQueues(GameEvent event, Entity host) {
		DiscoverEvent discoverEvent = (DiscoverEvent) event;

		CardType targetType = (CardType) getDesc().get(EventTriggerArg.CARD_TYPE);
		if (targetType != null && discoverEvent.getTargetType() != targetType) {
			return false;
		}

		return true;
	}

	@Override
	public GameEventType interestedIn() {
		return GameEventType.DISCOVER;
	}
}
