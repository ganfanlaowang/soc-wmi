package com.mynawang.wmi;

import com.alibaba.fastjson.JSON;
import com.mynawang.wmi.mq.NewTask;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.common.JISystem;
import org.jinterop.dcom.core.*;
import org.jinterop.dcom.impls.JIObjectFactory;
import org.jinterop.dcom.impls.automation.IJIDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by mynawang on 2017/8/28 0028.
 */
public class EventLogListener {


    private static final Logger logger = LoggerFactory.getLogger(EventLogListener.class);

    /**
     * 事件日志提供程序
     * 链接库文件：ntevt.dll
     * 命名空间：root\cimv2
     * 作用：包含从CIM Schema派生的类，它们代表着我们最常工作的win32环境。比如：获取系统信息，管理 Windows 事件日志等
     */
    private static final String WMI_DEFAULT_NAMESPACE = "ROOT\\CIMV2";

    /**
     * 【DCOM是COM的扩展，它可以支持不同计算机上组件对象与客户程序之间或者组件对象之间的相互通信】
     *
     * 配置和创建dcom连接
     * @param domain 域名，没有填""
     * @param user windows用户名（全名）
     * @param pass windows密码
     * @return
     * @throws Exception
     */
    private static JISession configAndConnectDCom(String domain, String user, String pass) throws Exception {
        JISystem.getLogger().setLevel(Level.WARNING);
        try {
            JISystem.setInBuiltLogHandler( false );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }
        JISystem.setAutoRegisteration(true);
        JISession dcomSession = JISession.createSession(domain, user, pass);
        dcomSession.useSessionSecurity(true);
        return dcomSession;
    }

    /**
     * 创建WbemScripting.SWbemLocator实例与WMI会话
     * @param host ip
     * @param dcomSession dcomSession会话
     * @return
     * @throws Exception
     */
    private static IJIDispatch getWmiLocator(String host, JISession dcomSession) throws Exception
    {
        JIComServer wbemLocatorComObj = new JIComServer(
                JIProgId.valueOf("WbemScripting.SWbemLocator"), host, dcomSession);
        return (IJIDispatch) JIObjectFactory.narrowObject(
                wbemLocatorComObj.createInstance().queryInterface(IJIDispatch.IID));
    }

    private static IJIDispatch toIDispatch(JIVariant comObjectAsVariant) throws JIException
    {
        return (IJIDispatch)JIObjectFactory.narrowObject(comObjectAsVariant.getObjectAsComObject());
    }


    public static void main( String[] args )
    {

        // Security、Application、System
        String domain = "";
        String host = "127.0.0.1";
        String user = "mynawang";
        String pass = "123456";
        JISession dcomSession = null;
        try
        {
            // Connect to DCOM on the remote system, and create an instance of the WbemScripting.SWbemLocator object to talk to WMI.
            // 远程系统中连接dcom
            dcomSession = configAndConnectDCom(domain, user, pass);
            // 创建WbemScripting.SWbemLocator实例与WMI会话
            IJIDispatch wbemLocator = getWmiLocator(host, dcomSession);

            // Invoke the "ConnectServer" method on the   object via it's IDispatch COM pointer. We will connect to
            // the default ROOT\CIMV2 namespace. This will result in us having a reference to a "SWbemServices" object.
            // 调用ConnectServer方法，连接默认的命名空间ROOT\CIMV2，返回SWbemServices
            JIVariant results[] =
                    wbemLocator.callMethodA(
                            "ConnectServer",
                            new Object[] {
                                new JIString(host),
                                new JIString(WMI_DEFAULT_NAMESPACE),
                                JIVariant.OPTIONAL_PARAM(),
                                JIVariant.OPTIONAL_PARAM(),
                                JIVariant.OPTIONAL_PARAM(),
                                JIVariant.OPTIONAL_PARAM(),
                                new Integer(0),
                                JIVariant.OPTIONAL_PARAM()
                            });

            IJIDispatch wbemServices = toIDispatch(results[0]);

            // Now that we have a SWbemServices DCOM object reference, we prepare a WMI Query Language (WQL) request to be informed whenever a
            // new instance of the "Win32_NTLogEvent" WMI class is created on the remote host. This is submitted to the remote host via the
            // "ExecNotificationQuery" method on SWbemServices. This gives us all events as they come in. Refer to WQL documentation to
            // learn how to restrict the query if you want a narrower focus.
            // 在远程机器上创建新的实例win32_ntlogevent后,使用获取到的SWbemServices用WQL进行查询,
            // 在SWbemServices有ExecNotificationQuery方法进行查询接收所有的事件
            final String QUERY_FOR_ALL_LOG_EVENTS =
                    "SELECT * FROM __InstanceCreationEvent WHERE " +
                            "TargetInstance ISA 'Win32_NTLogEvent'";

            /*final String QUERY_FOR_ALL_LOG_EVENTS_SYSTEM =
                    "SELECT * FROM __InstanceCreationEvent WHERE " +
                            "TargetInstance ISA 'Win32_NTLogEvent' " +
                            "and TargetInstance.LogFile = 'System'";*/

            final int RETURN_IMMEDIATE = 16;
            final int FORWARD_ONLY = 32;

            JIVariant[] eventSourceSet =
                    wbemServices.callMethodA(
                            "ExecNotificationQuery",
                            new Object[] {
                                new JIString(QUERY_FOR_ALL_LOG_EVENTS),
                                new JIString("WQL"),
                                new JIVariant(new Integer(RETURN_IMMEDIATE + FORWARD_ONLY ))
                            });
            IJIDispatch wbemEventSource = (IJIDispatch)JIObjectFactory.
                    narrowObject((eventSourceSet[0]).getObjectAsComObject());

            NewTask newTask = new NewTask();
            // The result of the query is a SWbemEventSource object. This object exposes a method that we can call in a loop to retrieve the
            // next Windows Event Log entry whenever it is created. This "NextEvent" operation will block until we are given an event.
            // Note that you can specify timeouts, see the Microsoft documentation for more details.

            // 查询得到SWbemEventSource对象，该对象公开了一种方法，可以在循环中调用这个方法检索下一个Windows事件日志
            // 这种操作在给出事件之前是一直阻塞的，可以设置超时时间进行控制
            while (true) {
                // this blocks until an event log entry appears.
                // 监听直到下一条事件日志出现
                JIVariant eventAsVariant = (JIVariant) (
                    wbemEventSource.callMethodA("NextEvent", new Object[] {JIVariant.OPTIONAL_PARAM()})
                )[0];
                IJIDispatch wbemEvent = toIDispatch(eventAsVariant);

                System.out.println("~~~~~~~~~~~~~~~~~~~~~" + JSON.toJSONString(wbemEvent));


                // WMI gives us events as SWbemObject instances (a base class of any WMI object). We know in our case we asked for a specific object
                // type, so we will go ahead and invoke methods supported by that Win32_NTLogEvent class via the wbemEvent IDispatch pointer.
                // In this case, we simply call the "GetObjectText_" method that returns us the entire object as a CIM formatted string. We could,
                // however, ask the object for its property values via wbemEvent.get("PropertyName"). See the j-interop documentation and examples
                // for how to query COM properties.
                // wmi提供一个wmi对象的基类，调用IDispatch获取特定类wbemEvent
                // 通过调用GetObjectText_方法返回对象转化为CIM格式的字符串
                // 可以调用wbemEvent.get("PropertyName")获取属性值
                JIVariant objTextAsVariant = (JIVariant) (
                    wbemEvent.callMethodA("GetObjectText_", new Object[] {new Integer( 1 )})
                )[0];
                String asText = objTextAsVariant.getObjectAsString().getString();

                newTask.sendMsg(asText);
                logger.info("==================" + asText);
            }
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != dcomSession)
            {
                try
                {
                    JISession.destroySession(dcomSession);
                }
                catch ( Exception ex )
                {
                    ex.printStackTrace();
                }
            }
        }
    }
}
