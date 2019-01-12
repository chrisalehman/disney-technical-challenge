#!/bin/bash

. ~/.bash_profile

# install sdk package manager
if [ -z "${SDKMAN_DIR}" ]; then
    curl -s "https://get.sdkman.io" | bash
    . "$HOME/.sdkman/bin/sdkman-init.sh"
else
    echo "Updating sdkman..."
    sdk update
fi
sdk version

# install java ecosystem
if [ -z `which java` ]; then
    sdk install java
fi
if [ -z `which gradle` ]; then
    sdk install gradle
fi

# install homebrew package manager
if [ -z `which brew` ]; then
    /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
else
    echo "Updating homebrew..."
    brew update
fi

# install git
if [ -z `which git` ]; then
    brew install git
fi

# cache git creds for 24 hours
git config credential.helper 'cache --timeout=86400'