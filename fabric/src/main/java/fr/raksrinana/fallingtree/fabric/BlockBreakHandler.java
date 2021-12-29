package fr.raksrinana.fallingtree.fabric;

import fr.raksrinana.fallingtree.fabric.config.BreakMode;
import fr.raksrinana.fallingtree.fabric.config.Configuration;
import fr.raksrinana.fallingtree.fabric.tree.breaking.BreakTreeTooBigException;
import fr.raksrinana.fallingtree.fabric.tree.breaking.ITreeBreakingHandler;
import fr.raksrinana.fallingtree.fabric.tree.breaking.InstantaneousTreeBreakingHandler;
import fr.raksrinana.fallingtree.fabric.tree.breaking.ShiftDownTreeBreakingHandler;
import fr.raksrinana.fallingtree.fabric.tree.builder.TreeBuilder;
import fr.raksrinana.fallingtree.fabric.tree.builder.TreeTooBigException;
import fr.raksrinana.fallingtree.fabric.utils.FallingTreeUtils;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockBreakHandler implements PlayerBlockBreakEvents.Before{
	@Override
	public boolean beforeBlockBreak(Level world, Player player, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity){
		if(Configuration.getInstance().getTrees().isTreeBreaking() && !world.isClientSide()){
			if(FallingTreeUtils.isPlayerInRightState(player, blockState)){
				try{
					var treeOptional = TreeBuilder.getTree(player, world, blockPos);
					if(treeOptional.isEmpty()){
						return true;
					}
					var tree = treeOptional.get();
					var breakMode = Configuration.getInstance().getTrees().getBreakMode();
					return getBreakingHandler(breakMode).breakTree(player, tree);
				}
				catch(TreeTooBigException e){
					FallingTreeUtils.notifyPlayer(player, new TranslatableComponent("chat.fallingtree.tree_too_big", Configuration.getInstance().getTrees().getMaxScanSize()));
					return true;
				}
				catch(BreakTreeTooBigException e){
					FallingTreeUtils.notifyPlayer(player, new TranslatableComponent("chat.fallingtree.break_tree_too_big", Configuration.getInstance().getTrees().getMaxSize()));
					return true;
				}
			}
		}
		return true;
	}
	
	public static ITreeBreakingHandler getBreakingHandler(BreakMode breakMode){
		return switch(breakMode){
			case INSTANTANEOUS -> InstantaneousTreeBreakingHandler.getInstance();
			case SHIFT_DOWN -> ShiftDownTreeBreakingHandler.getInstance();
		};
	}
}
