package me.coltify.bteams.utilities;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;
import org.bukkit.inventory.ItemStack;

public class UFile {

	private File file = null;
	private YamlConfiguration yaml = new YamlConfiguration();

	public UFile(File file) {
		this.file = file;
		createFile();

		load();
	}

	public UFile(String path) {
		this.file = new File(path);
		createFile();

		load();
	}

	public void add(String s, Object o) {
		if (!contains(s)) {
			set(s, o);
			save();
		}
	}

	public File toFile() {
		return file;
	}
	
	public void addToIntegerList(String s, int o) {
		this.yaml.getIntegerList(s).add(Integer.valueOf(o));
		save();
	}

	public void addToStringList(String s, String o) {
		this.yaml.getStringList(s).add(o);
		save();
	}

	public boolean contains(String s) {
		return this.yaml.contains(s);
	}

	public void createConfigurationSection(String s) {
		this.yaml.createSection(s);
		save();
	}

	public void createFile() {
		if (!this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void createNewIntegerList(String s, List<Integer> list) {
		this.yaml.set(s, list);
		save();
	}

	public void createNewStringList(String s, List<String> list) {
		this.yaml.set(s, list);
		save();
	}

	public void decrement(String s) {
		this.yaml.set(s, Integer.valueOf(getInt(s) - 1));
		save();
	}

	public void decrement(String s, int i) {
		this.yaml.set(s, Integer.valueOf(getInt(s) - i));
		save();
	}

	public void delete() {
		try {
			this.file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean doesFileExist() {
		if (!this.file.exists()) {
			return false;
		}
		return true;
	}

	public Object get(String s) {
		return this.yaml.get(s);
	}

	public boolean getBoolean(String s) {
		return this.yaml.getBoolean(s);
	}

	public ConfigurationSection getConfigurationSection(String s) {
		return this.yaml.getConfigurationSection(s);
	}

	public double getDouble(String s) {
		return this.yaml.getDouble(s);
	}

	public int getInt(String s) {
		return this.yaml.getInt(s);
	}

	public List<Integer> getIntegerList(String s) {
		return this.yaml.getIntegerList(s);
	}

	public ItemStack getItemStack(String path) {
		return this.yaml.getItemStack(path);
	}

	public String getString(String s) {
		return this.yaml.getString(s);
	}

	public List<String> getStringList(String s) {
		return this.yaml.getStringList(s);
	}

	public void increment(String s) {
		this.yaml.set(s, Integer.valueOf(getInt(s) + 1));
		save();
	}

	public void increment(String s, int i) {
		this.yaml.set(s, Integer.valueOf(getInt(s) + i));
		save();
	}

	private void load() {
		try {
			this.yaml.load(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public YamlConfigurationOptions options() {
		return this.yaml.options();
	}

	public void reload() {
		save();
		load();
	}

	public void remove(String s) {
		set(s, null);
		save();
	}

	public void removeFromIntegerList(String s, int o) {
		this.yaml.getIntegerList(s).remove(o);
		save();
	}

	public void removeFromStringList(String s, String o) {
		this.yaml.getStringList(s).remove(o);
		save();
	}

	public void save() {
		try {
			this.yaml.save(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void set(String s, Object o) {
		this.yaml.set(s, o);
		save();
	}
}
