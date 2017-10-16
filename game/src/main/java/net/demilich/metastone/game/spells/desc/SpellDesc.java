package net.demilich.metastone.game.spells.desc;

import net.demilich.metastone.game.cards.desc.Desc;
import net.demilich.metastone.game.logic.CustomCloneable;
import net.demilich.metastone.game.spells.Spell;
import net.demilich.metastone.game.spells.TargetPlayer;
import net.demilich.metastone.game.spells.desc.filter.EntityFilter;
import net.demilich.metastone.game.targeting.EntityReference;

import java.util.*;
import java.util.stream.Stream;

/**
 * A definition for a spell.
 * <p>
 * A spell description has a variety of arguments of type {@link SpellArg}.
 */
public class SpellDesc extends Desc<SpellArg> {
	public SpellDesc(Map<SpellArg, Object> arguments) {
		super(arguments);
	}

	public static Map<SpellArg, Object> build(Class<? extends Spell> spellClass) {
		final Map<SpellArg, Object> arguments = new EnumMap<>(SpellArg.class);
		arguments.put(SpellArg.CLASS, spellClass);
		return arguments;
	}

	public SpellDesc addArg(SpellArg spellArg, Object value) {
		SpellDesc clone = clone();
		clone.put(spellArg, value);
		return clone;
	}

	public SpellDesc removeArg(SpellArg spellArg) {
		SpellDesc clone = clone();
		clone.remove(spellArg);
		return clone;
	}

	@Override
	public SpellDesc clone() {
		SpellDesc clone = new SpellDesc(build(getSpellClass()));
		for (SpellArg spellArg : keySet()) {
			Object value = get(spellArg);
			if (value instanceof CustomCloneable) {
				CustomCloneable cloneable = (CustomCloneable) value;
				clone.put(spellArg, cloneable.clone());
			} else {
				clone.put(spellArg, value);
			}
		}
		return clone;
	}

	public EntityFilter getEntityFilter() {
		return (EntityFilter) get(SpellArg.FILTER);
	}

	public int getInt(SpellArg spellArg, int defaultValue) {
		return containsKey(spellArg) ? (int) get(spellArg) : defaultValue;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Spell> getSpellClass() {
		return (Class<? extends Spell>) get(SpellArg.CLASS);
	}

	public EntityReference getTarget() {
		return (EntityReference) get(SpellArg.TARGET);
	}

	public TargetPlayer getTargetPlayer() {
		return (TargetPlayer) get(SpellArg.TARGET_PLAYER);
	}

	public boolean hasPredefinedTarget() {
		return get(SpellArg.TARGET) != null;
	}

	@Override
	public String toString() {
		String result = "[SpellDesc arguments= {\n";
		for (SpellArg spellArg : keySet()) {
			result += "\t" + spellArg + ": " + get(spellArg) + "\n";
		}
		result += "}";
		return result;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		return super.equals(other);
	}

	public void setTarget(EntityReference target) {
		put(SpellArg.TARGET, target);
	}

	public Stream<SpellDesc> subSpells() {
		Stream<SpellDesc> spells;
		SpellDesc[] spellsArray = (SpellDesc[]) get(SpellArg.SPELLS);
		if (spellsArray.length > 0) {
			spells = Stream.concat(Stream.of(spellsArray),
					Stream.of(spellsArray).flatMap(SpellDesc::subSpells)
			);
		} else {
			spells = Stream.empty();
		}
		Stream<SpellDesc> unitSpells = Stream.of(SpellArg.SPELL, SpellArg.SPELL_1, SpellArg.SPELL_2)
				.map(this::get)
				.filter(Objects::nonNull)
				.map(o -> (SpellDesc) o)
				.flatMap(SpellDesc::subSpells);

		return Stream.concat(spells, unitSpells);
	}
}
