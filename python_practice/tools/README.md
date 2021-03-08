
# Practiced
* Testing
  * pytest
* Profiling
  * CProfile
  * Timeit

# Not Practiced
* Versioning:
  * PEP 440
* Style errors
  * pep8
  * Ignore errors: pep8 --ignore=E3 hello.py
  * Error is 3 digit number, hundreds digit indicates category of error
* Static analyzers
  * flake8 - pyflakes + pep8
    * used by most open source projects
    * intellij integration?
  * pyflakes - no pep8 checks
  * pylint - Pep8 checks + coding errors
* Modules
  * Import module w/ unknown name: random = __import__("RANDOM".lower())
  * List of modules imported: sys.modules['os'], etc..
  * List of built-in modules: sys.builtin_module_names
* Package Installation
  * Install a package w/o copying package's files
    * Use case: install a package that you're actively working on
    * pip install -e .
    * this places an "*.egg-link" file in distribution path, this file contains path to add to sys.path
  * Install library directly from git repo
    * pip install -e git+https://github.com/jd/daiquiri.git\#egg=daiquiri
    * provide package egg name by adding #egg= at the end of URL
    * this cmd clones repo, and creates egg-link pointing to cloned dir 
    * careful: no versioning used, so next commit in remote repo could break things
