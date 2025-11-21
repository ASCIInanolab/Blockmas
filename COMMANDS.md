# Blockmas Commands Reference

## Player Commands

### /calendar
**Open the advent calendar GUI**
- Permission: `blockmas.open` (default: true)
- Usage: `/calendar`
- Shows all days 1-24
- Click orange doors to open (if available)
- Green = already opened
- Gray = not available yet

### /calendar admin
**Open the admin panel** (requires permission)
- Permission: `blockmas.admin` (default: op)
- Usage: `/calendar admin`
- Click any day to edit
- Select material from GUI
- Type amount in chat (1-64)

## Admin Commands

### /blockmas admin reset-playtime
**Reset a player's playtime** (allows re-opening doors)
- Permission: `blockmas.admin`
- Usage: `/blockmas admin reset-playtime <player>`
- Example: `/blockmas admin reset-playtime Steve`
- Effect: Player can open doors again (if playtime was required)

### /blockmas admin reset-doors
**Reset opened doors** (player starts fresh)
- Permission: `blockmas.admin`
- Usage: `/blockmas admin reset-doors <player>`
- Example: `/blockmas admin reset-doors Steve`
- Effect: All claimed doors become unclaimed

### /blockmas admin reset-all
**Reset everything** (playtime + doors)
- Permission: `blockmas.admin`
- Usage: `/blockmas admin reset-all <player>`
- Example: `/blockmas admin reset-all Steve`
- Effect: Complete reset, player starts from day 1

## How to Edit Items

1. Run `/calendar admin`
2. Click on a day (1-24)
3. Select a material from the GUI
4. Type amount in chat (1-64)
5. **Changes save instantly!**

## Admin Panel Guide

**Material Selector** shows:
- DIAMOND, EMERALD, GOLD_INGOT, IRON_INGOT
- CHEST, ENCHANTED_BOOK, APPLE, BREAD
- DIAMOND_BLOCK, IRON_BLOCK, GOLD_BLOCK, COAL
- And many more...

**Custom Materials**:
If the material you want isn't in the selector, edit config.yml directly:
```yaml
items:
  1:
    material: YOUR_MATERIAL_NAME
    amount: 1
```

## Admin Settings

### Per-Day Playtime Requirements
Edit `config.yml`:
```yaml
items:
  1:
    material: DIAMOND
    amount: 1
    min-play-seconds: 0      # No requirement
  2:
    material: GOLD
    amount: 1
    min-play-seconds: 600    # 10 minutes
```

### Change Locale
Edit `config.yml`:
```yaml
locale: en    # English
locale: de    # German
```

### Change Timezone
Edit `config.yml`:
```yaml
time-zone: UTC              # Reset at UTC midnight
time-zone: Europe/Berlin    # Reset at Berlin midnight
time-zone: US/Eastern       # Reset at US East midnight
```

## Permission Node Hierarchy

```
blockmas.*                  # All permissions
├── blockmas.open          # Open calendar
├── blockmas.admin         # Admin access
│   ├── blockmas.admin.edit       # Edit items
│   └── blockmas.admin.give       # Grant items
└── blockmas.use           # Legacy (kept for compatibility)
```

## Common Tasks

### Create a playtime-gated day
```yaml
items:
  10:
    material: BEACON
    amount: 1
    min-play-seconds: 3600  # 1 hour required
```

### Reset player data
```
/blockmas admin reset-all <player>
```

### Change item for day 5
1. `/calendar admin`
2. Click day 5
3. Select new material
4. Type amount (e.g., 64)

### Disable playtime for all days
Edit config.yml, set all `min-play-seconds: 0`

---

For setup help, see [INSTALLATION.md](INSTALLATION.md)
