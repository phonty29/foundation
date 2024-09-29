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
has_bin = false

if [ -n "$executable_files" ]; then

  for file in $executable_files; do
    if [[ "$file" == *.sh ]]; then
      continue
    fi
    
    echo "- $file"
    has_bin = true
  done

  if [ "$has_bin" = true]; then
    echo "\n\033[0;31m The following executable files were found:\033[0m"
    exit 1
  fi
fi
EOF

# Make the pre-commit hook executable
chmod +x "$FILE"

echo "Pre-commit hook installed successfully."