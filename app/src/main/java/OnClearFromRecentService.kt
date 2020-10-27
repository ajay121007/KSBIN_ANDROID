import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.koin.standalone.KoinComponent

/**
 * Created by skycap.
 */
class OnClearFromRecentService : Service(),KoinComponent{

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }
}