package com.xmqbeast.blockchain;


import java.util.ArrayList;

import com.xmqbeast.blockchain.util.JsonUtil;

public class BlockChainListTest {

    public static ArrayList<Block> blockChain = new ArrayList();

    public static int difficulty = 5;

    public static void main(String[] args) {
        blockChain.add(new Block("first", "0"));
        System.out.println("Trying to Mine block 1... ");
        blockChain.get(0).mineBlock(difficulty);

        blockChain.add(new Block("second", blockChain.get(blockChain.size() - 1).hash));
        System.out.println("Trying to Mine block 2... ");
        blockChain.get(1).mineBlock(difficulty);

        blockChain.add(new Block("third", blockChain.get(blockChain.size() - 1).hash));
        System.out.println("Trying to Mine block 3... ");
        blockChain.get(2).mineBlock(difficulty);


        System.out.println("\nBlockchain is Valid: " + isChainValid());
//        String json = new GsonBuilder().setPrettyPrinting().create().toJson(blockChain);

        System.out.println(JsonUtil.toJson(blockChain));
    }

    public static Boolean isChainValid(){

        Block currentBlock;
        Block previousBlock;
        boolean flag = true;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        //循环遍历列表检验hash
        for(int i=1;i<blockChain.size();i++){
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i-1);
            //比较注册的hash和计算的hash
            if(!currentBlock.hash.equals(currentBlock.calculateHash())){
                System.out.println("当前hash不相等");
                flag=false;
            }
            //比较当前的前一个hash与注册的前一个hash
            if(!previousBlock.hash.equals(currentBlock.previousHash)){
                System.out.println("前一个hash不相等");
                flag=false;
            }

            //check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("This block hasn't been mined");
                flag=false;
            }
        }

        return flag;
    }
}
