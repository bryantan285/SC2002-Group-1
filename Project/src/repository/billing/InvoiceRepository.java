package repository.billing;

import entity.billing.Invoice;
import java.io.IOException;
import java.util.Iterator;
import repository.Repository;

public class InvoiceRepository extends Repository<Invoice> {

    private static InvoiceRepository repo = null;
    private static final String FILE_PATH = "Project\\data\\Invoice_List.csv";
    private static final String PREFIX = "INV";

    public static void main(String[] args) {
        try {
            InvoiceRepository repo = InvoiceRepository.getInstance();
            Iterator<Invoice> iterator = repo.iterator();

            System.out.println("Total Invoices: " + repo.getSize());
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private InvoiceRepository() throws IOException {
        super();
        load();
    }

    public static InvoiceRepository getInstance() {
        if (repo == null) {
            try {
                repo = new InvoiceRepository();
            } catch (IOException e) {
                System.err.println("Failed to create InvoiceRepository instance: " + e.getMessage());
                throw new RuntimeException("Failed to initialize InvoiceRepository", e);
            }
        }
        return repo;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String getFilePath() {
        return FILE_PATH;
    }

    @Override
    public Class<Invoice> getEntityClass() {
        return Invoice.class;
    }
}
