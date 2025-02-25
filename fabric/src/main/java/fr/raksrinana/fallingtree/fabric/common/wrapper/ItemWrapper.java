package fr.raksrinana.fallingtree.fabric.common.wrapper;

import fr.raksrinana.fallingtree.common.wrapper.IBlockState;
import fr.raksrinana.fallingtree.common.wrapper.IItem;
import fr.raksrinana.fallingtree.common.wrapper.IItemStack;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@ToString
public class ItemWrapper implements IItem{
	@NotNull
	@Getter
	private final Item raw;
	
	@Override
	public boolean isAxe(){
		return raw instanceof AxeItem;
	}
	
	@Override
	public boolean isAir(){
		return Items.AIR.equals(raw);
	}
	
	@Override
	public float getDestroySpeed(@NotNull IItemStack itemStack, @NotNull IBlockState blockState){
		return raw.getDestroySpeed((ItemStack) itemStack.getRaw(), (BlockState) blockState.getRaw());
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof IItem item)){
			return false;
		}
		return raw.equals(item.getRaw());
	}
	
	@Override
	public int hashCode(){
		return raw.hashCode();
	}
}
