# Changelog

## [1.0.0] - 2025-11-21

### ✨ Initial Release

#### Features
- **Advent Calendar GUI**: 27-slot inventory with 24 configurable days
- **Per-Day Playtime Tracking**: Each day can require minimum playtime before opening
- **Admin Panel**: Full GUI-based management interface
- **Multi-Language Support**: English and German included
- **Persistent Storage**: YAML-based per-player data management
- **Admin Commands**: Reset playtime, doors, or all data
- **Organized Code**: Clean package structure (command/, listener/, manager/, util/)
- **Lightweight**: Only 29 KB JAR file

#### Packages
```
command/
  - CalendarCommand.java (player calendar + admin GUI)
  - AdminCommand.java (reset playtime/doors/all)

listener/
  - InventoryListener.java (door click handling)
  - PlaytimeManager.java (session tracking)
  - AdminEditListener.java (admin GUI interactions)

manager/
  - PlayerDataManager.java (YAML player data storage)
  - GUIManager.java (calendar display)
  - AdminGUIManager.java (admin panel display)

util/
  - LocaleManager.java (EN/DE translations)
  - LuckPermsHook.java (optional role integration)
```

#### Configuration
- Locale support (en/de)
- Timezone-aware daily resets
- Per-day item customization
- Per-day playtime requirements

#### Commands
- `/calendar` - Open advent calendar
- `/calendar admin` - Open admin panel
- `/blockmas admin reset-playtime <player>` - Reset playtime
- `/blockmas admin reset-doors <player>` - Reset opened doors
- `/blockmas admin reset-all <player>` - Reset everything

#### Permissions
- `blockmas.open` (default: true) - Open calendar
- `blockmas.admin` (default: op) - Admin access

#### Documentation
- README.md - Quick start & overview
- INSTALLATION.md - Setup guide with examples
- COMMANDS.md - Command reference
- CHANGELOG.md - Version history

### 🐛 Bug Fixes (in development)
- Fixed playtime tracking using UUID instead of Player objects
- Fixed daily playtime counter reset logic
- Fixed data preservation when updating player files
- Fixed English localization (was showing German)

### 📦 Technical Details
- **Compatibility**: Paper 1.20+
- **Java**: 17+
- **Build**: Maven 3.10+
- **JAR Size**: 29 KB
- **Dependencies**: Paper API (provided scope), LuckPerms API (optional)

### 🎯 Known Limitations
- Playtime resets only on server restart if no quit event
  - Mitigation: Proper server shutdown
- LuckPerms group calendars prepared but not fully implemented
  - Can be added in future versions

### 📝 Next Steps (Future Versions)
- [ ] Per-group calendar overrides (LuckPerms)
- [ ] Anvil GUI for amount input (better UX)
- [ ] Admin playtime view/statistics
- [ ] Custom skull heads as door decorations
- [ ] Advanced particle effects
- [ ] Leaderboard functionality
- [ ] Multi-calendar support

---

**Build Date**: November 21, 2025  
**Status**: Production Ready ✅  
**License**: MIT
