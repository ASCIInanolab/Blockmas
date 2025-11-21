# Blockmas - Advent Calendar Plugin

A lightweight, fully customizable Minecraft advent calendar plugin for Paper 1.20+. Features English/German localization, per-day playtime tracking, and intuitive GUI-based administration.

## ✨ Features

- **Beautiful Calendar GUI**: 27-slot inventory showing days 1-24
- **Multi-language**: English & German (easily extendable)
- **Per-Day Playtime Tracking**: Each day can require minimum playtime
- **Admin Panel**: Full GUI-based management (no chat commands needed)
- **Persistent Storage**: Per-player data stored in YAML files
- **Lightweight**: Only 29 KB JAR, minimal resource usage
- **Fully Customizable**: Configure items, amounts, and playtime per day
- **Optional LuckPerms Integration**: Role-based calendars (when installed)

## 🚀 Quick Start

### Downloads:

- **Modrinth:** https://modrinth.com/plugin/blockmas
- **GitHub Releases**

### Installation
1. Download `Blockmas-1.0.0.jar` from releases
2. Place in your server's `plugins/` folder
3. Restart server
4. Edit `plugins/Blockmas/config.yml` to customize
5. Done!

### Basic Commands
```
/calendar              # Open your advent calendar
/calendar admin        # Open admin panel (requires blockmas.admin)
/blockmas admin reset-playtime <player>  # Reset player's playtime
/blockmas admin reset-doors <player>     # Reset opened doors
/blockmas admin reset-all <player>       # Reset everything
```

## 📋 Configuration

### config.yml
```yaml
# Locale: 'en' for English, 'de' for German
locale: en

# Timezone for determining current day
time-zone: UTC

# Calendar range
start-day: 1
end-day: 24

# Items per day
items:
  1:
    material: DIAMOND
    amount: 1
    min-play-seconds: 0
  2:
    material: GOLD_INGOT
    amount: 3
    min-play-seconds: 600  # 10 minutes required
  # ... add more days (3-24)
```

## 🔑 Permissions

| Permission | Default | Purpose |
|-----------|---------|---------|
| `blockmas.open` | true | Open calendar |
| `blockmas.admin` | op | Access admin panel & commands |

## 🎮 How to Use

### For Players
1. Type `/calendar` to open the advent calendar
2. Click on available doors (orange) to claim your item
3. **If playtime required**: Play first, then click door
4. Green doors = already claimed
5. Gray doors = not available yet

### For Admins
1. Type `/calendar admin` to open admin panel
2. Click any day to edit
3. Select material from GUI
4. Type amount in chat (1-64)
5. **Changes save immediately!**

### Admin Commands
```bash
/blockmas admin reset-playtime <player>  # Reset player's playtime
/blockmas admin reset-doors <player>     # Reset opened doors
/blockmas admin reset-all <player>       # Reset everything
```

## �� Per-Day Playtime System

Each day can have its own playtime requirement:

```yaml
items:
  1:
    material: DIAMOND
    amount: 1
    min-play-seconds: 0        # No requirement
  2:
    material: GOLD_INGOT
    amount: 3
    min-play-seconds: 600      # 10 minutes required TODAY
  3:
    material: IRON_INGOT
    amount: 5
    min-play-seconds: 1800     # 30 minutes required TODAY
```

**How it works:**
- Playtime **resets daily at midnight** (based on timezone)
- Each day requires playtime to be met TODAY
- Admin can reset with `/blockmas admin reset-playtime <player>`

## 📁 Project Structure

```
src/main/java/dev/blockmas/blockmas/
├── BlockmasPlugin.java
├── command/
│   ├── CalendarCommand.java
│   └── AdminCommand.java
├── listener/
│   ├── InventoryListener.java
│   ├── PlaytimeManager.java
│   └── AdminEditListener.java
├── manager/
│   ├── PlayerDataManager.java
│   ├── GUIManager.java
│   └── AdminGUIManager.java
└── util/
    ├── LocaleManager.java
    └── LuckPermsHook.java
```

## 📄 License

MIT License

---

**Version**: 1.0.0 | **Paper 1.20+** | **Java 17+**
