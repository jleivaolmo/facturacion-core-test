package com.echevarne.sap.cloud.facturacion.model;

import java.util.concurrent.ScheduledFuture;

public interface CronExecution {

    String getProcessId();
    void cancel();
    void setCancellator(ScheduledFuture<?> cancellator);

}
