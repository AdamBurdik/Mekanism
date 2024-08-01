package me.adamix.mekanism.blocks.capabilities;

public interface OutputCapability extends Capability {
	boolean[] getOutputSides();
	void setOutputSides(boolean[] outputSides);
}
