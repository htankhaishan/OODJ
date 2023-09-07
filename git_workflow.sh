#!/bin/bash

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

# Prompt the user to proceed or cancel
echo "Step 1: git status"
prompt_proceed_cancel || exit 1
git status
status_code=$?
echo "Git status exit status code: $status_code"
if [ $status_code -ne 0 ]; then
  echo "Error: git status command failed."
  exit 1
fi

# Step 2: git add .
echo "Step 2: git add ."
git add .
status_code=$?
echo "Git add exit status code: $status_code"
if [ $status_code -ne 0 ]; then
  echo "Error: git add command failed."
  exit 1
fi

# Step 3: git commit -m "commit name"
echo "Step 3: git commit -m \"commit name\""
read -r -p "Enter the commit message: " commit_message
git commit -m "$commit_message"
status_code=$?
echo "Git commit exit status code: $status_code"
if [ $status_code -ne 0 ]; then
  echo "Error: git commit command failed."
  exit 1
fi

# Step 4: git pull origin main
echo "Step 4: git pull origin main"
prompt_proceed_cancel || exit 1
git pull origin main
status_code=$?
echo "Git pull exit status code: $status_code"
if [ $status_code -ne 0 ]; then
  echo "Error: git pull command failed."
  exit 1
fi

# Step 5: git push origin main
echo "Step 5: git push origin main"
prompt_proceed_cancel || exit 1
git push origin main
status_code=$?
echo "Git push exit status code: $status_code"
if [ $status_code -ne 0 ]; then
  echo "Error: git push command failed."
  exit 1
fi

echo "All steps completed successfully."

