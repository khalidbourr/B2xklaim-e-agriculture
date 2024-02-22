#!/bin/bash
# Source the user's .bashrc to make sure all functions and environment variables are available
source ~/.bashrc

# Navigate to the workspace
cd ~/dev_ws

# Source the setup script for the workspace
source install/setup.bash

# Set up the ROS version
source /opt/ros/galactic/setup.bash

# Set up the Tello workspace
echo "Setting up Tello workspace..."
export GAZEBO_PLUGIN_PATH=${GAZEBO_PLUGIN_PATH}:${HOME}/dev_ws/install/tello_gazebo/lib
export LD_LIBRARY_PATH=${HOME}/dev_ws/install/tello_msgs/lib:${LD_LIBRARY_PATH}


# Launch the ROS 2 launch file
ros2 launch agriculture multi_launch.py
