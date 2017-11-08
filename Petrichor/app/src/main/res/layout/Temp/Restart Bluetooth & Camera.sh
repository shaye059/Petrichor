#!/bin/bash

sudo launchctl stop com.apple.blued
sudo launchctl start com.apple.blued

sudo kextunload -b com.apple.iokit.BroadcomBluetoothHostControllerUSBTransport
sudo kextload -b com.apple.iokit.BroadcomBluetoothHostControllerUSBTransport

sudo killall VDCAssistant