package wayos.car.updateEngine;

public abstract class ICarUpdateEngineCallback {
   public abstract void onStatusUpdate(int status, float percent);
   public abstract void onPayloadApplicationComplete(int errorCode);
}
