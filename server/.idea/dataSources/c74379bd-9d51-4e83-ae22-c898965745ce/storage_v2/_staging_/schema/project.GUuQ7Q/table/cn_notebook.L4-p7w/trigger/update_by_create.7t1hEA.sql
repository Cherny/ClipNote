CREATE TRIGGER update_by_create
  AFTER UPDATE
  ON cn_notebook
  FOR EACH ROW
  BEGIN
  INSERT INTO cn_notebook(body, date_time) VALUE (NEW.body, now());
  DELETE FROM cn_notebook WHERE id = OLD.id;
END;

