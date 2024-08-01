package me.adamix.mekanism.blocks.capabilities;


public interface InputCapability extends Capability {
	boolean[] getInputSides();
	void setInputSides(boolean[] inputSides);
}