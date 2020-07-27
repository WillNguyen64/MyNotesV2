
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
* Standard library
  * CLIs
    * argparse - CLI arg parsing, alternative: click
  * data processing
    * codecs - encode/decode data
    * copy - copy data  
  * data structures / algorithms
    * abc - abstract base classes (https://pymotw.com/3/abc/)
    * bisect - bisection algorithms for sorting
    * collections - useful data structures
  * date/time
    * calendar - date-related funcs
    * datetime - handle dates/times  
  * managing files
    * fnmatch - matching Unix-style filename patterns
    * glob - matching Unix-style path patterns
    * io - handle I/O streams, use StringIO to treat strings as files
    * shutil - 
    * tempfile - create temp files/dirs
  * async processing
    * concurrent - async compute
    * multiprocessing - run subprocesses with thread-like API
    * sched - event scheduler w/o using multithreading
    * threading - high-level therading
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