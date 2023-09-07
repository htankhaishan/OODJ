#!/bin/bash

# Function to print the exit status code
function print_exit_status() {
  echo "Exit status code: $1"
}

# Function to prompt the user to proceed or cancel
function prompt_proceed_cancel() {
  read -r -p "Proceed? [Y/n]: " response
  case "$response" in
    [yY][eE][sS]|[yY]|"")
      return 0
      ;;
    [nN][oO]|[nN])
      return 1
      ;;
    *)
      echo "Invalid input. Please type 'Y' to proceed or 'N' to cancel."
      prompt_proceed_cancel
      ;;
  esac
}

# Step 1: git add src/oodj/OODJ.java
echo "Step 1: git add src/oodj/OODJ.java"
prompt_proceed_cancel || exit 1
git add src/oodj/OODJ.java
status_code=$?
print_exit_status $status_code
if [ $status_code -ne 0 ]; then
  echo "Error: git add command failed."
  exit 1
fi

# Step 2: git commit -m "Merge conflict resolved"
echo "Step 2: git commit -m \"Merge conflict resolved\""
prompt_proceed_cancel || exit 1
git commit -m "Merge conflict resolved"
status_code=$?
print_exit_status $status_code
if [ $status_code -ne 0 ]; then
  echo "Error: git commit command failed."
  exit 1
fi

# Step 3: git push origin main
echo "Step 3: git push origin main"
prompt_proceed_cancel || exit 1
git push origin main
status_code=$?
print_exit_status $status_code
if [ $status_code -ne 0 ]; then
  echo "Error: git push command failed."
  exit 1
fi

echo "All steps completed successfully."

