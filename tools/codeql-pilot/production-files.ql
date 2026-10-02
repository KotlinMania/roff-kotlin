import java

from File f
where f.getRelativePath().matches("src/%") and f.getExtension() = "kt" and
  exists(Callable c | c.getFile() = f)
select f.getRelativePath()
