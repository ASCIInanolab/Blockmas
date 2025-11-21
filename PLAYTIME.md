# Playtime System - Troubleshooting

## How Playtime Works

### Recording
1. When a player **joins** → timestamp recorded (UUID-based)
2. When a player **quits** → session duration calculated and added to total
3. Data stored in: `plugins/Blockmas/playerdata/{UUID}.yml`

### Opening Doors
- Each day requires: `min-play-seconds` (default: 0)
- Player's total playtime is checked before allowing door open
- If playtime insufficient → shows message with remaining time needed

## Configuration

### Enable Playtime Requirement
Edit `plugins/Blockmas/config.yml`:

```yaml
# Seconds required before opening ANY door
min-play-seconds: 3600  # 1 hour = 3600 seconds

# Alternative common values:
# 300 = 5 minutes
# 600 = 10 minutes
# 1800 = 30 minutes
# 3600 = 1 hour
# 86400 = 1 day
```

## Checking Player Data

Player data files are stored in: `plugins/Blockmas/playerdata/`

Example file: `plugins/Blockmas/playerdata/550e8400-e29b-41d4-a716-446655440000.yml`

```yaml
claimed:
  - 1
  - 2
  - 3
playtime: 7200  # seconds (2 hours)
```

## Resetting Player Data

### Delete all playtime for a player
1. Stop the server
2. Open: `plugins/Blockmas/playerdata/{player-uuid}.yml`
3. Change: `playtime: 0`
4. Save and restart

### Delete all players
```bash
# Back up first!
rm -rf plugins/Blockmas/playerdata/*
```

## Debugging

### Enable Debug Logging
Uncomment this line in `PlaytimeManager.java`:

```java
plugin.getLogger().info("[Playtime] " + p.getName() + " played " + sessionSeconds + "s (total: " + totalSeconds + "s)");
```

Then watch `logs/latest.log` for playtime messages.

### Common Issues

#### Playtime not tracking
- ❌ Player joined before plugin loaded → first join is lost
- ✅ Restart server after installing plugin
- ✅ Make sure `PlaytimeManager` is registered in `BlockmasPlugin.java`

#### Playtime incorrect
- Check timezone in config (affects which day it is)
- Verify `min-play-seconds` value is correct
- Check player data files exist in `playerdata/` folder

#### Reset a player's playtime
1. Stop server
2. Edit their `.yml` file: `playtime: 0`
3. Restart

---

**Fixed in latest version:**
- Changed from `Player` object keys to `UUID` keys in HashMap (was causing data loss)
- Fixed millisecond/second calculation
- Added playtime preservation when updating claimed doors
- Improved error logging
