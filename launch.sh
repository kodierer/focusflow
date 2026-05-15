#!/bin/bash
# FocusFlow Launch Script
# Automatisiert alle Schritte zum Live-Gehen

set -e
echo " FocusFlow Launch Automation"
echo "================================"

# Colors
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

# Step 1: Repository Setup
echo -e "${YELLOW}Step 1: Git Repository Setup${NC}"
if ! git rev-parse --git-dir > /dev/null 2>&1; then
    echo "Initializing Git..."
    git init
    git add .
    git commit -m "FocusFlow Initial Commit"
    echo -e "${GREEN}✓ Git initialized${NC}"
else
    echo -e "${GREEN}✓ Git already initialized${NC}"
fi

# Step 2: Gradle Build
echo -e "${YELLOW}Step 2: Building APK/Bundle${NC}"
if [ -f "./gradlew" ]; then
    chmod +x ./gradlew
    echo "Building Release Bundle..."
    ./gradlew bundleRelease --info
    echo -e "${GREEN}✓ Build complete! Bundle at: app/build/outputs/bundle/release/app-release.aab${NC}"
else
    echo -e "${RED}✗ Gradle wrapper not found${NC}"
    exit 1
fi

# Step 3: Info
echo -e "${YELLOW}Step 3: Next Steps${NC}"
echo -e "${GREEN}✓ Build successful!${NC}"
echo ""
echo "Next steps:"
echo "1. Upload to Google Play Console:"
echo "   - https://play.google.com/console"
echo "   - App Name: FocusFlow - Pomodoro Timer"
echo "   - Bundle: app/build/outputs/bundle/release/app-release.aab"
echo ""
echo "2. Fill in app details:"
echo "   - See DEPLOYMENT_GUIDE.md for templates"
echo ""
echo "3. Submit for review!"
echo ""
echo -e "${GREEN}That's it! You're going live!${NC} "
