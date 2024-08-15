#!/usr/bin/env bash
set -e

PWD="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

. ${HOME}/.bash_profile
. ${PWD}/env-setup-pkg-mgr.sh

### install java ecosystem ###
install_sdkman
install_sdkman_package java 8.0.422-tem
install_sdkman_package gradle

### install homebrew packages ###
install_homebrew
install_brew_package git
install_brew_cask_package docker
install_brew_cask_package pgadmin4
install_brew_cask_package postman

# cache git creds for 24 hours
git config credential.helper 'cache --timeout=86400'
