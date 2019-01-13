#!/usr/bin/env bash
set -e

### SDKMAN ###

function install_sdkman() {
    if [ -z "${SDKMAN_DIR}" ]; then
        curl -s "https://get.sdkman.io" | bash
        . "$HOME/.sdkman/bin/sdkman-init.sh"
    else
        echo "Updating sdkman..."
        sdk update
    fi
    sdk version
}

function install_sdkman_package() {
    if [ -z "`sdk ls $1`" ]; then
        sdk install $1
    else
        sdk current $1
    fi
}

### HOMEBREW ###

function install_homebrew() {
    if [ -z "`which brew`" ]; then
        /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
    else
        echo "Updating homebrew..."
        brew update
    fi
}

function install_brew_package() {
    if [ -z "`brew ls $1 --versions`" ]; then
        brew install $1
    else
        brew ls $1 --versions
    fi
}

function install_brew_cask_package() {
    if [ -z "`brew cask ls $1 --versions`" ]; then
        brew cask install $1
    else
        brew cask ls $1 --versions
    fi
}