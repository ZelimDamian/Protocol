directory.ProductView = Backbone.View.extend({

    render: function () {
        this.$el.html(this.template(this.model.toJSON()));
        return this;
    }
});

directory.ProductSummaryView = Backbone.View.extend({

    initialize:function (options) {
    	_.bindAll(this);
    	this.model = new directory.Product();
        this.model.on("change", this.render, this);
        this.collection = options.collection;
    },

    render:function () {
        this.$el.html(this.template(this.model.toJSON()));
        
        $name = this.$el.find('#name');
        $desc = this.$el.find('#description');
        $price = this.$el.find('#price');
        $cert = this.$el.find('#cert');
        
        //this.$("#createProduct").click(this.createProduct);

        return this;
    },

    updateSaveModel:function()
    {
    	var data = {
    			name : $name.val(),
    			description : $desc.val(),
    			price: parseInt($price.val()),
    			cert : $cert.val()
    	};
    	this.model.save(data);
    	this.collection.add(this.model);
    },

    setModel:function(newModel)
    {
    	this.model = newModel;
    	this.render();
    },
    
    createProduct: function()
    {
    	this.updateSaveModel();
    },
    
    events: {
    	"click #saveProduct" : this.createProduct
    }

});