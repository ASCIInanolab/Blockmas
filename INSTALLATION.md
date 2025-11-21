# Installation Guide

## Step-by-Step Setup

### 1. Download Plugin
- Download `Blockmas-1.0.0.jar` from GitHub Releases
- Or build from source: `mvn clean package`

### 2. Install JAR
```bash
# Copy JAR to plugins folder
cp Blockmas-1.0.0.jar /path/to/server/plugins/

# Restart server
# Server will auto-generate config.yml
```

### 3. Configure Plugin
Edit `plugins/Blockmas/config.yml`:

```yaml
# Language
locale: en              # 'en' or 'de'

# Timezone (affects daily reset time)
time-zone: UTC          # UTC, Europe/Berlin, US/Eastern, etc.

# Calendar settings
start-day: 1
end-day: 24

# Configure items for each day
items:
  1:
    material: DIAMOND
    amount: 1
    min-play-seconds: 0
  2:
    material: GOLD_INGOT
    amount: 3
    min-play-seconds: 600
  # ... repeat for days 3-24
```

### 4. Restart Server
```bash
/stop
# Wait 10 seconds
# Start server again
```

### 5. Test Installation
```bash
# In-game, as a player:
/calendar

# As op/admin:
/calendar admin
```

## Configuration Examples

### No Playtime Required
```yaml
items:
  1:
    material: DIAMOND
    amount: 1
    min-play-seconds: 0    # Players can open anytime
```

### 10 Minute Requirement
```yaml
items:
  2:
    material: EMERALD
    amount: 2
    min-play-seconds: 600  # 60 * 10 = 600 seconds
```

### 1 Hour Requirement
```yaml
items:
  3:
    material: NETHERITE_INGOT
    amount: 1
    min-play-seconds: 3600 # 60 * 60 = 3600 seconds
```

### 30 Minutes Requirement
```yaml
items:
  4:
    material: BEACON
    amount: 1
    min-play-seconds: 1800 # 60 * 30 = 1800 seconds
```

## Common Playtime Values
- 5 minutes: `300`
- 10 minutes: `600`
- 15 minutes: `900`
- 30 minutes: `1800`
- 1 hour: `3600`
- 2 hours: `7200`
- 4 hours: `14400`
- 8 hours: `28800`

## Timezone Examples
- UTC: `UTC`
- Europe/Berlin: `Europe/Berlin`
- Europe/London: `Europe/London`
- US/Eastern: `US/Eastern`
- US/Pacific: `US/Pacific`
- Australia/Sydney: `Australia/Sydney`

## Troubleshooting

### Plugin doesn't load?
- Check server log for errors
- Verify Java 17+: `java -version`
- Check Paper version 1.20+: `/version`

### Config not applying?
- Restart server (config loads on startup)
- Check file is valid YAML (proper indentation)

### Playtime not tracking?
- Verify `min-play-seconds` > 0 in config
- Check player joined AFTER plugin loaded
- Use `/blockmas admin reset-playtime <player>` to reset if stuck

### Materials not showing?
- Use Minecraft material names (CAPS_WITH_UNDERSCORES)
- Invalid materials become AIR
- Full list: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html

## Backup & Reset

### Backup Player Data
```bash
# Backup before updates
cp -r plugins/Blockmas/playerdata plugins/Blockmas/playerdata.backup
```

### Reset Specific Player
```bash
# Delete player file (they start fresh)
rm plugins/Blockmas/playerdata/{UUID}.yml
```

### Reset All Players
```bash
# WARNING: This resets everyone!
rm -rf plugins/Blockmas/playerdata/*
```

## Uninstall
```bash
# Remove plugin
rm plugins/Blockmas-1.0.0.jar

# Optionally keep player data
# Or delete: rm -rf plugins/Blockmas
```

---

For command help, see [README.md](README.md)
