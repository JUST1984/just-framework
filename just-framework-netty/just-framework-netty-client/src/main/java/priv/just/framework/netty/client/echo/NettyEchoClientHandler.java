package priv.just.framework.netty.client.echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: yixiezi1994@gmail.com
 * @date: 2020-04-20 15:34
 */
@Slf4j
@ChannelHandler.Sharable
public class NettyEchoClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        int len = in.readableBytes();
        byte[] arr = new byte[len];
        in.getBytes(0, arr);
        log.info("client receive：【{}】", new String(arr, StandardCharsets.UTF_8));
        in.release();
    }
}
