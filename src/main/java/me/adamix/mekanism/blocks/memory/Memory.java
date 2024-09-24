package me.adamix.mekanism.blocks.memory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Memory {
	private final Map<String, Object> memory = new HashMap<>();

	public void set(String key, Object object) {
		memory.put(key, object);
	}

	public Object get(String key) {
		return memory.get(key);
	}

	public String getString(String key) {
		return (String) get(key);
	}

	public int getInt(String key) {
		return (int) get(key);
	}

	public double getDouble(String key) {
		return (double) get(key);
	}

	public float getFloat(String key) {
		return (float) get(key);
	}

	public long getLong(String key) {
		return (long) get(key);
	}

	public boolean getBoolean(String key) {
		return (boolean) get(key);
	}

	public byte getByte(String key) {
		return (byte) get(key);
	}

	public char getChar(String key) {
		return (char) get(key);
	}

	public short getShort(String key) {
		return (short) get(key);
	}

	public UUID getUUID(String key) {
		return (UUID) get(key);
	}

	public boolean[] getBooleanArray(String key) {
		return (boolean[]) get(key);
	}

	@Override
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(memory);
	}
}
