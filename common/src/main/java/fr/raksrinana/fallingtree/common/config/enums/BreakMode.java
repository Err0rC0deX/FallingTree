package fr.raksrinana.fallingtree.common.config.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BreakMode{
	INSTANTANEOUS(true, true),
	SHIFT_DOWN(true, false);
	
	private final boolean checkLeavesAround;
	private final boolean applySpeedMultiplier;
}
