#!/bin/sh

DIR=".git/hooks"
FILE="$DIR/pre-commit"

if [ ! -d "$DIR" ]; then
  echo "Error: not a git repository"
  exit 1
fi

cat << 'EOF' > "$FILE"
#!/bin/sh

executable_files=$(find . -type f -executable ! -path "./.git/*")

if [ -n "$executable_files" ]; then

  has_exe = false
  for file in $executable_files; do
    if [["$file" == *.sh]]; then
      continue
    fi
    
    echo "- $file"
    has_exe = true
  done

  if [ "$has_exe" = true]; then
    echo "\n\033[0;31m The following executable files were found:\033[0m"
    exit 1
  fi
fi
EOF

# Make the pre-commit hook executable
chmod +x "$FILE"

echo "Pre-commit hook installed successfully."