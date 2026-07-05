- Adjust the logic of SeasonHooks.getBiomeTemperature so that cold biomes now have a minimum temperature of 0.15+ when
  it is not snowing. This prevents mods such as **Snow Under Trees** from accumulating snow during rainy weather in cold
  biomes.