#!/usr/bin/env bash
set -e

### Use this script to upgrade MacOS system bash to latest bash ###

function upgrade_bash() {
    BASH_VERSION=`bash -version | grep bash | awk '{print $4}'`
    DEFAULT_VERSION='3.2.57(1)-release'

    # system bash is older version shipped with MacOS; update it to latest bash
    if [ "${BASH_VERSION}" == "${DEFAULT_VERSION}" ]; then

        # install homebrew bash
        if [ -z "`brew ls bash --versions`" ]; then
            brew install bash
        else
            brew upgrade bash
        fi

        echo "*******************************************************************************"
        echo "To complete bash setup, execute the following commands:"
        echo ">> sudo su - root"
        echo ">> echo '/usr/local/bin/bash' >> /etc/shells"
        echo ">> chsh -s /usr/local/bin/bash"
        echo ">> exit"
        echo "*******************************************************************************"
    fi
}

upgrade_bash