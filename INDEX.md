# Blockmas - Complete Documentation Index

## 🎯 Start Here
- **[README.md](README.md)** - Overview, features, quick start
- **[INSTALLATION.md](INSTALLATION.md)** - Step-by-step setup guide

## 📖 User Guides
- **[COMMANDS.md](COMMANDS.md)** - All commands and how to use them
- **[PLAYTIME.md](PLAYTIME.md)** - Per-day playtime system explained

## 👨‍💻 Development
- **[CHANGELOG.md](CHANGELOG.md)** - Version history and releases
- **[FIXES.md](FIXES.md)** - Bug fixes and improvements

## 📁 Project Structure

```
Blockmas/
├── pom.xml                          # Maven build configuration
├── src/
│   └── main/
│       ├── java/dev/blockmas/blockmas/
│       │   ├── BlockmasPlugin.java                    # Main entry point
│       │   ├── command/
│       │   │   ├── CalendarCommand.java               # /calendar command
│       │   │   └── AdminCommand.java                  # /blockmas admin
│       │   ├── listener/
│       │   │   ├── InventoryListener.java             # Door clicks
│       │   │   ├── PlaytimeManager.java               # Playtime tracking
│       │   │   └── AdminEditListener.java             # Admin GUI
│       │   ├── manager/
│       │   │   ├── PlayerDataManager.java             # Data storage
│       │   │   ├── GUIManager.java                    # Calendar GUI
│       │   │   └── AdminGUIManager.java               # Admin panel
│       │   └── util/
│       │       ├── LocaleManager.java                 # EN/DE support
│       │       └── LuckPermsHook.java                 # Optional LP
│       └── resources/
│           ├── plugin.yml                             # Plugin metadata
│           └── config.yml                             # Default config
├── target/
│   └── Blockmas-1.0.0.jar          # Compiled plugin (29 KB)
└── Documentation
    ├── README.md                    # Main documentation
    ├── INSTALLATION.md              # Setup guide
    ├── COMMANDS.md                  # Command reference
    ├── PLAYTIME.md                  # Playtime system
    ├── CHANGELOG.md                 # Version history
    ├── FIXES.md                     # Bug fixes
    └── INDEX.md                     # This file
```

## 🚀 Quick Links

### For New Users
1. Read [README.md](README.md) for overview
2. Follow [INSTALLATION.md](INSTALLATION.md) to install
3. Check [COMMANDS.md](COMMANDS.md) for available commands

### For Admins
1. Configure `config.yml` in game folder
2. Use `/calendar admin` to edit items via GUI
3. Use `/blockmas admin` commands to manage players

### For Developers
- Java 17+, Paper API 1.20+
- Maven build: `mvn clean package`
- Clean package structure with separation of concerns
- Well-documented code with comments

## 📊 Statistics

| Metric | Value |
|--------|-------|
| JAR Size | 29 KB |
| Java Files | 10 |
| Documentation Pages | 6 |
| Supported Languages | 2 (EN, DE) |
| Commands | 5 |
| Permissions | 2 main + 2 granular |

## ✨ Key Features

✅ Per-day playtime requirements  
✅ GUI-only administration  
✅ English & German localization  
✅ Persistent player data (YAML)  
✅ Admin commands for data reset  
✅ Lightweight & efficient  
✅ Well-organized code  
✅ Complete documentation  

## 🔧 Configuration

### config.yml Location
`plugins/Blockmas/config.yml`

### Key Settings
- `locale` - Language (en/de)
- `time-zone` - Daily reset timezone
- `items` - Configure each day

### Player Data
Stored in: `plugins/Blockmas/playerdata/{UUID}.yml`

## 📞 Support

- Issues: Check [INSTALLATION.md](INSTALLATION.md) Troubleshooting
- Questions: Review [COMMANDS.md](COMMANDS.md)
- Playtime help: See [PLAYTIME.md](PLAYTIME.md)

## 🎓 Learning Path

1. **Beginner**: Read README.md
2. **Installation**: Follow INSTALLATION.md
3. **Usage**: Learn COMMANDS.md
4. **Advanced**: Understand PLAYTIME.md config

## 📄 License

MIT License - See LICENSE file

---

**Version**: 1.0.0  
**Last Updated**: November 21, 2025  
**Status**: Production Ready ✅
