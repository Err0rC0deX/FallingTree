package fr.raksrinana.fallingtree.common.wrapper;

import org.jetbrains.annotations.NotNull;

public interface IChunk extends IWrapper{
	@NotNull
	IChunkPos getPos();
}
