
# Setup pyenv
```
$ git clone https://github.com/pyenv/pyenv.git ~/.pyenv
$ echo 'export PYENV_ROOT="$HOME/.pyenv"' >> ~/.zshrc
$ echo 'export PATH="$PYENV_ROOT/bin:$PATH"' >> ~/.zshrc
$ echo -e 'if command -v pyenv 1>/dev/null 2>&1; then\n  eval "$(pyenv init -)"\nfi' >> ~/.zshrc
$ echo "$SHELL"
$ pyenv -v
pyenv 1.2.18-1-g0f2d6597
# Before install Python, need to install Xcode to install tools required by Python installer such as xcrun
$ xcode-select --install
$ pyenv install 3.7.7
$ pyenv install 3.8.2
```

# Install pyenv
```
$ git clone https://github.com/pyenv/pyenv-virtualenv.git $(pyenv root)/plugins/pyenv-virtualenv
$ echo 'eval "$(pyenv virtualenv-init -)"' >> ~/.zshrc
$ echo "$SHELL"
$ pyenv virtualenv 3.8.2 pytutorial-3.8.2
$ cd <your project>
$ pyenv local pytutorial-3.8.2
$ cat .python-version
pytutorial-3.8.2
```

# Install VSCode extensions
- Docker
- IntelliJ IDEA Keybindings
- Python
- Visual Sutdio IntelliCode

# Setup VSCode project
- Select `pytutorial-3.8.2` from the Python interpreter list in the bottom taskbar
- pip install -r requirements.txt

# Setup settings.json
- TODO

# Setup launch.json
- TODO
