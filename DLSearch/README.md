
Setup 
```
curl -L https://github.com/lihaoyi/mill/releases/download/0.7.3/0.7.3-example-3.zip \\n  -o example-3.zip
unzip example-3.zip
rm -f *.zip; mv example-3/* .; rm -fr example-3
chmod +x mill
./mill foo.compile
./mill foo run
./mill foo assembly
./mill show foo.assembly
./out/foo/assembly/dest/out.jar 
./mill mill.scalalib.GenIdea/idea

```

