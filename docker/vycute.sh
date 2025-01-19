!/bin/bash
# allow local ip
allow_ip(){
  echo ">>Setup tour ip config on localhost:5143 ...."
  docker exec -it postgres-tour-db bash -c "echo 'host    all    all    0.0.0.0/0    md5' >> /var/lib/postgresql/data/pg_hba.conf"
  docker exec -it postgres-tour-db bash -c "echo 'host    all    all    ::/0    md5' >> /var/lib/postgresql/data/pg_hba.conf"
  echo "Allow localhost:5143 successfull"
}

# Create database in postgres-tour-db
create_postgres_db() {
  echo "Creating database in postgres-tour-db..."
  docker exec -i postgres-tour-db psql -U postgres -c "CREATE DATABASE tourdb;" || {
    echo "Failed to create database tourdb in postgres-tour-db."; exit 1;
  }
  echo "Database tourdb created in postgres-tour-db."
}


Generate all databases
generate_databases() {
  echo "Generating all required databases..."
  allow_ip
  create_postgres_db
  echo "All databases generated successfully."
}

Main script execution
generate_databases